#!/usr/bin/env python
# -*- coding:utf-8 -*-
"""
    __author__= Floyd
    __time__= 2017/4/20 20:49
"""

import os
import sys
import json
from subprocess import check_output
import commands

if __name__=='__main__':
    # if len(sys.argv)!=2:
    #     print 'python CheckAllProjects.py project_location_file'
    file = open('project_location', 'r')
    jar = "E:\\wenzi\\checkstyle-7.6.1-all.jar"
    xml = "E:\\wenzi\\google_checks.xml"
    folder = sys.path[0] + '\\checkresult'
    location = {}
    for line in file.readlines():
        line = line.strip()
        location = json.loads(line)
    for k, v in location.iteritems():
        print '***************** group %s **********************' % k
        for item in v:
            print '\t'
            cmd = 'java -jar %s -c %s %s ' % (jar, xml, item )
            print '\t%s' % cmd
            file_name = '%s\\%s.checkstyle.result.%d' % (folder, k, v.index(item))
            result_file = open(file_name, 'w')
            # status, output = commands.getstatusoutput(cmd)
            try:
                result_file.write(check_output(cmd, shell=True))
            except BaseException:
                continue
