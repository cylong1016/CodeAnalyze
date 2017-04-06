#!/usr/bin/env python
# -*- coding:utf-8 -*-  
"""
    __author__= Floyd
    __time__= 2017/3/16 19:29
"""
import sys
import re
import MySQLdb
REGEX = r'\[([^\]]*)\](.*)\[([^\]]*)\]'
OMIT = ['Indentation', 'CustomImportOrder', ]

class MysqlConnection:
    def __init__(self, ip, user, password, database_name):
        self.db = MySQLdb.connect(ip, user, password, database_name)
        self.cursor = self.db.cursor()

    def execute(self, sql):
        # try:
        return self.cursor.execute(sql)
        # self.db.commit()
        # except:
        #     self.db.rollback()
        #     return False

    def commit(self):
        return self.db.commit()

    def get_last_id(self):
        return self.cursor.lastrowid

    def get_all(self):
        return self.cursor.fetchall()

    def close(self):
        return self.db.close()


if __name__ == '__main__':
    # if len(sys.argv) < 2:
    #     print 'Usage:\n\tpython %s __FILENAME__' % sys.argv[0]
    #     exit(1)
    # checkstyle_file = open(sys.argv[1], 'r')
    checkstyle_file = open('checkstyle.result', 'r')
    # mysql = MysqlConnection('localhost', 'root', '', 'code_analyze')
    for line in checkstyle_file.xreadlines():
        line = line.strip()
        macthObj = re.match(REGEX, line)
        if macthObj is None:
            continue
        father_type = macthObj.group(1)
        sub_type = macthObj.group(3)
        # 消除 分区头F:\ 的影响
        check_info = (macthObj.group(2).split(':\\'))[1].split(':')
        file_name = '\/'.join(check_info[0].split('\\src\\')[1].split('\\'))
        row, column, description = 0, 0, None
        try:
            row = int(check_info[1])
        except ValueError:
            description = check_info[1].strip()
        try:
            column = int(check_info[2])
        except ValueError:
            description = check_info[2].strip()
        if description is None:
            description = ''.join(check_info[3:]).strip()
        sql_result = 'INSERT INTO checkstyle_result \
              (father_type, sub_type, file, row, col, check_id, description, group_id) \
              VALUES ("%s", "%s", "%s", "%d", "%d", "%d", "%s","%d") ' % \
                     (father_type, sub_type, file_name, row, column, 1, description, 1)
        print father_type, sub_type, file_name, row, column, description
        mysql.execute(sql_result)
    mysql.commit()
    mysql.close()
