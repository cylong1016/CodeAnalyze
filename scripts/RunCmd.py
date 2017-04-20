#!/usr/bin/env python
# -*- coding:utf-8 -*-
"""
    __author__= Floyd
    __time__= 2017/4/20 13:49
"""

import os
import sys

if __name__=='__main__':
    if len(sys.argv)!=5:
        print 'Usage: python RunCmd.py all_projects_dir jar_location xml_location results_location'
    father_dir = sys.argv[1]
    jar = sys.argv[2]
    xml = sys.argv[3]
    result_dir = sys[4]
    # father_dir = 'E:\\test'
    folders = [father_dir]
    results = []
    project_names = []
    for project in os.listdir(father_dir):
        project_names.append(project)

    for folder in folders:
        folders += [os.path.join(folder, x) for x in os.listdir(folder) \
                    if os.path.isdir(os.path.join(folder, x))]
        results += [os.path.join(folder, x)  \
                    for x in os.listdir(folder) \
                    if  os.path.isdir(os.path.join(folder, x )) and x=='src' ]
    print project_names
    print results
    for src in results:
        for project in project_names:
            if project in src:
                cmd = 'java -jar %s -c %s %s > %s\\%s.checkstyle.result ' % ('jar', 'xml', 'src', 'result_dir', project)
                print cmd
                os.system(cmd)
                break
