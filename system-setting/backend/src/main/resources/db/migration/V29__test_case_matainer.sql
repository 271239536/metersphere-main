SET SESSION innodb_lock_wait_timeout = 7200;
ALTER TABLE test_case
    MODIFY maintainer varchar(50) NOT NULL COMMENT 'Test case maintainer';
SET SESSION innodb_lock_wait_timeout = DEFAULT;
