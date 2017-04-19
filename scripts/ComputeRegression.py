#!/usr/bin/env python
# -*- coding:utf-8 -*-
"""
    __author__= Floyd
    __time__= 2017/4/16 14:52

"""

import MySQLdb


INTERNAL_TYPE = ["Annotations",
                 "Block Checks",
                 "Class Design",
                 "Coding",
                 "Header",
                 "Imports",
                 "Javadoc Comments",
                 "Metrics",
                 "Miscellaneous",
                 "Modifiers",
                 "Naming Conventions",
                 "Regexp",
                 "Size Violations",
                 "WhiteSpaces",
                 "All"]
check_id = 4

db = MySQLdb.connect('localhost', 'root', '', 'code_analyze', charset="utf8")
cursor = db.cursor()

sql_stat = "SELECT * FROM checkstyle_stat_result WHERE check_id=%d" % check_id
type_stat = {}
try:
    cursor.execute(sql_stat)
    for item in cursor.fetchall():
        if item[3] not in type_stat.keys():
            type_stat[item[3]] = {}
        type_stat[item[3]][item[4]] = float(item[2])
    print type_stat
except BaseException:
    print 'Error'

sql_grade = "SELECT * FROM checkstyle_grade WHERE check_id=%d" % 4
grades = {}
try:
    cursor.execute(sql_grade)
    for item in cursor.fetchall():
        grades[item[4]] = float(item[3])
except BaseException:
    print 'Error_2'

for type in INTERNAL_TYPE:
    x = []
    y = []
    for k, grade in grades.iteritems():
        y.append(grade)
        x.append(type_stat[k][type])
    print x
    print y
    x_sum, y_sum = sum(x), sum(y)
    x_avg, y_avg = x_sum/len(x), y_sum/len(y)
    xi_2_sum, xi_yi_sum = 0, 0
    for i in range(0, len(x)):
        xi_2_sum += x[i]**2
        xi_yi_sum += x[i]*y[i]
    try:
        cffc_b = (xi_yi_sum - (x_sum * y_sum / len(x))) / (xi_2_sum - (x_sum**2 / len(x)))
        cffc_a = y_avg - cffc_b * x_avg
        print cffc_a, cffc_b, type
        print '********************'
    except BaseException:
        cffc_a, cffc_b = 0, 0
        print '********************'

    sql_cffc = 'INSERT INTO checkstyle_regression(check_id, cffc_a, cffc_b, internal_type) VALUES ("%s", %f, %f, "%s")' % (check_id, cffc_a, cffc_b, type)
    cursor.execute(sql_cffc)

db.commit()
db.close()
