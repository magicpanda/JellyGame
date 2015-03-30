# JellyGame
JellyGame Backend Implement

##Spec
果冻消除（服务器端）
有⼀个果冻消除游戏，这个消除游戏的基本规则是：
1. 有⼀个8x8的棋盘，棋盘每个格⼦可以⽤⼀个⼆维坐标表⽰[row, col]. 其中从上到下依
次是第0, 1, ……7⾏，从左到右依次是第0, 1, ……7列；
2. 棋盘的每个格⼦中有⼀个果冻，可以是以下四种类型中的⼀种：普通果冻、横炸弹果冻、竖
炸弹果冻和⽅炸弹果冻；
3. 每次⽤户操作时，会在棋盘上选择⼀个矩形区域(AABB)。该矩形可⽤其左上⾓和右下⾓的坐
标来确定，⽐如[0,1] [2,2]；
4. 矩形内的所有元素都会被消除，对于[0,1] [2,2]这个矩形来说，这6个元素都会被消除:
[0,1], [0,2], [1,1], [1,2], [2,1], [2,2]；
5. 炸弹果冻在消除后会有特殊的效果:横炸弹会消除掉所在⾏的所有果冻；竖炸弹会消除掉所在
列的所有果冻；⽅炸弹会消除掉周围⼀圈8个果冻；
6. 被炸弹消除的果冻也按照第5条规则处理；
7. 消除结束后，被消掉的地⽅会由上⽅的果冻掉落进⾏填补。第0⾏的元素被消掉（或下落到下
⽅）时，会由系统随机⽣成⼀个果冻进⾏填补(4种果冻的⽣成概率均等)。

##Rules
1. 开始某⼀关卡
/start-level?level=<关卡编号>
返回：
第⼀⾏为⼀个字符串(sessionId)，⽤于唯⼀标识⼀个进⾏中的关卡，sessionId的格式可以⾃
⾏确定；
后续8⾏表⽰关卡初始布局，对应棋盘中的8⾏，每⾏包括8个字符。每个字符表⽰⼀种果冻，对应
关系为：（B普通果冻，H横炸弹果冻，V竖炸弹果冻，S⽅炸弹果冻）
2. ⼀次消除操作
/move?
sessionId=<sessionId>&row0=<row0>&col0=<col0>&row1=<row1>&col1=<col1>
参数：
sessionId：第2个接⼝返回的sessionId
row0, col0, row1, col1表⽰⼀次操作的左上⾓和右下⾓的果冻坐标，⽐如：
row0=0&col1=1&row1=2&row2=2
返回：
8行文本，表示消除结束且完成下落后的局⾯，格式与接⼝1的返回中后8⾏相同。

##Example
举例：
请求：/start-level?level=1
响应：
40ab3f2j
HBBBSBBB
BBBBBBBB
BSBBBBBB
BBBBVBBB
BHBBBBBB
SBBBBSBB
BBBBBBBB
BBBBBBBB
请求：/move?sessionId=40ab3f2j&row0=1&col0=0&row1=1&col1=2
响应：
VSBBSBBB
HBBBBBBB
BSBBBBBB
BBBBVBBB
BHBBBBBB
SBBBBSBB
BBBBBBBB
BBBBBBBB
说明：消除了第1⾏的前3个普通果冻后，第0⾏的前3个果冻掉落到第1⾏，第0⾏由系统补充了3个果冻，分
别是V、S、B
请求：/move?sessionId=40ab3f2j&row0=5&col0=0&row1=6&col1=2
响应
BBSBBBBH
SBBBSBBB
BBHBBBBB
VSBBBBBB
HBBBVBBB
BSBBBSBB
BBBBBBBB
BBBBBBBB
说明：初始消除了5个普通果冻和⼀个⽅炸弹，⽅炸弹爆炸时，炸到了[4,0]的普通果冻和[4,1]的横炸
弹，炸弹将第4⾏全部消除
##Notice
1. 使⽤关系型数据库做持久化存储
2. 每⼀关的初始布局保存在数据库中
3. ⽀持多个⽤户并⾏玩游戏，游戏服务器重启或切换（如负载均衡）后不影响⽤户游戏进程
4. 所有接⼝均以HTTP GET请求的⽅式由客户端向服务器发送，服务器以HTTP响应的⽅式返回
给客户端，返回格式为纯⽂本（不带任何HTML标签）
5. 如果⽤户给出的参数⾮法（⽆论是类型还是数值），仅返回⼀⾏⽂本：”INVALID PARAMS”
6. 最后提交的项⺫需要包含完整的项目⽂件和源代码，以及创建数据库的相关脚本

### Performance
In the following experiments, the spec of the computer used can be found in the following table

| Component      | Spec                                    |
|----------------|-----------------------------------------|
| Computer Model | 戴尔 System Vostro 3460                 |
| Processor      | 2.20GHz Intel Core i7 3632QM            |
| Memory         | 12GB 1600 MHz DDR3                      |
| Software       | Windows 8.1 64位                        |
| Jvm            | Oracle JDK 1.7.0_25                     |

#### Load testing with Jmeter
We use [Apache Jmeter](http://jmeter.apache.org) for load testing, and the load testing script can be found in `${JellyGame}/jmeter.jmx`.


|sampler_label	|aggregate_report_count	|average	|aggregate_report_min	|aggregate_report_max	|aggregate_report_stddev	|aggregate_report_error%	|aggregate_report_rate	|aggregate_report_bandwidth	|average_bytes|
|-----|------|-----|-----|-------|------|--------|--------|-------|
|startLevel	|50000	|232	|28	|22227	|719.2044911	|0	|200.837089	|46.57485054	|237.46932|
|总体	|50000	|232	|28	|22227	|719.2044911	|0	|200.837089	|46.57485054	|237.46932|


