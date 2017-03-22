#!/usr/bin/env python
# -*- coding:utf-8 -*-  
"""
    __author__= Floyd
    __time__= 2017/3/16 19:29
"""
import sys
import MySQLdb


class MysqlConnection:
    def __init__(self, ip, user, password, database_name):
        self.db = MySQLdb.connect(ip, user, password, database_name)
        self.cursor = self.db.cursor()

    def execute(self, sql):
        # try:
        self.cursor.execute(sql)
        self.db.commit()
        # except:
        #     self.db.rollback()
        #     return False

    def close(self):
        return self.db.close()


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print 'Usage:\n\tpython %s __FILENAME__' % sys.argv[0]
        exit(1)
    checkstyle_file = open(sys.argv[1], 'r')
    # checkstyle_file = open('small_log.txt', 'r')
    mysql = MysqlConnection('localhost', 'root', '', 'code_analyze')
    for line in checkstyle_file.xreadlines():
        line = line.strip()
        attr = line.split(']', 1)
        if len(attr) < 2:
            continue
        father_type = attr[0][1:]
        attr2 = attr[1].strip().split('[', 1)
        sub_type = attr2[1][:-1]
        # 消除 分区头F:\ 的影响
        check_info = (attr2[0].split(':\\'))[1].split(':')
        file_name = check_info[0]
        row, column, detail = 0, 0, None
        try:
            row = int(check_info[1])
        except ValueError:
            detail = check_info[1].strip()
        try:
            column = int(check_info[2])
        except ValueError:
            detail = check_info[2].strip()
        if detail is None:
            detail = ''.join(check_info[3:]).strip()
        print '%s\t%s\t%s\t%d\t%d\t%s' % (father_type, sub_type, file_name, row, column, detail)

        sql = 'INSERT INTO checkstyle_result \
              (father_type, sub_type, file, row, col, add_time, description) \
              VALUES ("%s", "%s", "%s", "%d", "%d", "%d", "%s") ' % \
              (1, 1, file_name, row, column, 1, detail)
        print mysql.execute(sql)

    mysql.close()
