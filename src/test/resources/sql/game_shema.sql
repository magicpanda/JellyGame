USE jelly_game_test;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS level_layout;

CREATE TABLE user (
  id            BIGINT                       AUTO_INCREMENT,
  name          VARCHAR(128),
  description   VARCHAR(255),
  install_time  BIGINT(20) UNSIGNED NOT NULL DEFAULT '0',
  sessionId     VARCHAR(255),
  currentLevel  INT(11),
  currentLayout VARCHAR(1700),
  currentStep   INT(10),
  PRIMARY KEY (id),
  KEY sessionId (sessionId)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE level_layout (
  id     INT           AUTO_INCREMENT,
  level  INT(11) NOT NULL,
  layout VARCHAR(1700) DEFAULT '',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;