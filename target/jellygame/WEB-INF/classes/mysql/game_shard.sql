use jelly_game;
drop table if exists user;
# drop table if exists level_layout;

create table user (
  id bigint auto_increment,
  name varchar(128),
  description varchar(255),
  install_time bigint(20) unsigned NOT NULL DEFAULT '0',
  sessionId varchar(255),
  currentLevel int(11),
  currentLayout varchar(1500),
  currentStep int(10),
  primary key (id)
) engine=InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE level_layout (
  id INT AUTO_INCREMENT,
  level int(11) NOT NULL,
  layout VARCHAR(1500) DEFAULT '',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;