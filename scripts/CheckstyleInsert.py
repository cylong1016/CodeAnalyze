#!/usr/bin/env python
# -*- coding:utf-8 -*-  
"""
    __author__= Floyd
    __time__= 2017/3/16 19:29
"""
import json
import re
import MySQLdb
import random

REGEX = r'\[([^\]]*)\](.*)\[([^\]]*)\]'
OMIT = ['Indentation', 'CustomImportOrder', 'FileTabCharacter', 'WhitespaceAround', 'AvoidStarImport',
        'EmptyLineSeparator']
TREE = {"Annotations": ["AnnotationLocation", "AnnotationUseStyle", "MissingDeprecated", "MissingOverride",
                        "PackageAnnotation", "SuppressWarnings", "SuppressWarningsHolder"],
        "Block Checks": ["AvoidNestedBlocks", "EmptyBlock", "EmptyCatchBlock", "LeftCurly", "NeedBraces",
                         "RightCurly"],
        "Class Design": ["DesignForExtension", "FinalClass", "HideUtilityClassConstructor", "InnerTypeLast",
                         "InterfaceIsType", "MutableException", "OneTopLevelClass", "ThrowsCount",
                         "VisibilityModifier"],
        "Coding": ["ArrayTrailingComma", "AvoidInlineConditionals", "CovariantEquals", "DeclarationOrder",
                   "DefaultComesLast", "EmptyStatement", "EqualsAvoidNull", "EqualsHashCode",
                   "ExplicitInitialization", "FallThrough", "FinalLocalVariable", "HiddenField", "IllegalCatch",
                   "IllegalInstantiation", "IllegalThrows", "IllegalToken", "IllegalTokenText", "IllegalType",
                   "InnerAssignment", "MagicNumber", "MissingCtor", "MissingSwitchDefault",
                   "ModifiedControlVariable", "MultipleStringLiterals", "MultipleVariableDeclarations",
                   "NestedForDepth", "NestedIfDepth", "NestedTryDepth", "NoClone", "NoFinalizer",
                   "OneStatementPerLine", "OverloadMethodsDeclarationOrder", "PackageDeclaration",
                   "ParameterAssignment", "RequireThis", "ReturnCount", "SimplifyBooleanExpression",
                   "SimplifyBooleanReturn", "StringLiteralEquality", "SuperClone", "SuperFinalize",
                   "UnnecessaryParentheses", "VariableDeclarationUsageDistance"],
        "Header": ["Header", "RegexpHeader"],
        "Imports": ["AvoidStarImport", "AvoidStaticImport", "CustomImportOrder", "IllegalImport", "ImportControl",
                    "ImportOrder", "RedundantImport", "UnusedImports"],
        "Javadoc Comments": ["AtclauseOrder", "JavadocMethod", "JavadocPackage", "JavadocParagraph", "JavadocStyle",
                             "JavadocTagContinuationIndentation", "JavadocType", "JavadocVariable",
                             "NonEmptyAtclauseDescription", "SingleLineJavadoc", "SummaryJavadoc", "WriteTag"],
        "Metrics": ["BooleanExpressionComplexity", "ClassDataAbstractionCoupling", "ClassFanOutComplexity",
                    "CyclomaticComplexity", "JavaNCSS", "NPathComplexity"],
        "Miscellaneous": ["ArrayTypeStyle", "AvoidEscapedUnicodeCharacters", "CommentsIndentation",
                          "DescendantToken", "FileContentsHolder", "FinalParameters", "Indentation",
                          "NewlineAtEndOfFile", "OuterTypeFilename", "TodoComment", "TrailingComment",
                          "Translation", "UncommentedMain", "UniqueProperties", "UpperEll"],
        "Modifiers": ["ModifierOrder", "RedundantModifier"],
        "Naming Conventions": ["AbbreviationAsWordInName", "AbstractClassName", "CatchParameterName",
                               "ClassTypeParameterName", "ConstantName", "InterfaceTypeParameterName",
                               "LocalFinalVariableName", "LocalVariableName", "MemberName", "MethodName",
                               "MethodTypeParameterName", "PackageName", "ParameterName", "StaticVariableName",
                               "TypeName"],
        "Regexp": ["Regexp", "RegexpMultiline", "RegexpOnFilename", "RegexpSingleline", "RegexpSinglelineJava"],
        "Size Violations": ["AnonInnerLength", "ExecutableStatementCount", "FileLength", "LineLength",
                            "MethodCount", "MethodLength", "OuterTypeNumber", "ParameterNumber"],
        "WhiteSpaces": ["EmptyForInitializerPad", "EmptyForIteratorPad", "EmptyLineSeparator", "FileTabCharacter",
                        "GenericWhitespace", "MethodParamPad", "NoLineWrap", "NoWhitespaceAfter",
                        "NoWhitespaceBefore", "OperatorWrap", "ParenPad", "SeparatorWrap", "SingleSpaceSeparator",
                        "TypecastParenPad", "WhitespaceAfter", "WhitespaceAround"]}

REVERSE_TREE = {
    "AnnotationLocation": "Annotations", "AnnotationUseStyle": "Annotations", "MissingDeprecated": "Annotations",
                   "MissingOverride": "Annotations","PackageAnnotation": "Annotations", "SuppressWarnings": "Annotations",
                   "SuppressWarningsHolder": "Annotations",

    "AvoidNestedBlocks": "Block Checks", "EmptyBlock": "Block Checks", "EmptyCatchBlock": "Block Checks", "LeftCurly": "Block Checks",
                   "NeedBraces": "Block Checks", "RightCurly": "Block Checks",

    "DesignForExtension": "Class Design", "FinalClass": "Class Design", "HideUtilityClassConstructor": "Class Design",
                   "InnerTypeLast": "Class Design", "InterfaceIsType": "Class Design", "MutableException": "Class Design",
                   "OneTopLevelClass": "Class Design", "ThrowsCount": "Class Design", "VisibilityModifier": "Class Design",

    "ArrayTrailingComma": "Coding", "AvoidInlineConditionals": "Coding", "CovariantEquals": "Coding", "DeclarationOrder": "Coding",
                   "DefaultComesLast": "Coding", "EmptyStatement": "Coding", "EqualsAvoidNull": "Coding", "EqualsHashCode": "Coding",
                   "ExplicitInitialization": "Coding", "FallThrough": "Coding", "FinalLocalVariable": "Coding", "HiddenField": "Coding",
                   "IllegalCatch": "Coding", "IllegalInstantiation": "Coding", "IllegalThrows": "Coding", "IllegalToken": "Coding",
                   "IllegalTokenText": "Coding", "IllegalType": "Coding", "InnerAssignment": "Coding", "MagicNumber": "Coding",
                   "MissingCtor": "Coding", "MissingSwitchDefault": "Coding", "ModifiedControlVariable": "Coding",
                   "MultipleStringLiterals": "Coding", "MultipleVariableDeclarations": "Coding", "NestedForDepth": "Coding",
                   "NestedIfDepth": "Coding", "NestedTryDepth": "Coding", "NoClone": "Coding", "NoFinalizer": "Coding",
                   "OneStatementPerLine": "Coding", "OverloadMethodsDeclarationOrder": "Coding", "PackageDeclaration": "Coding",
                   "ParameterAssignment": "Coding", "RequireThis": "Coding", "ReturnCount": "Coding", "SimplifyBooleanExpression": "Coding",
                   "SimplifyBooleanReturn": "Coding", "StringLiteralEquality": "Coding", "SuperClone": "Coding", "SuperFinalize": "Coding",
                   "UnnecessaryParentheses": "Coding", "VariableDeclarationUsageDistance": "Coding",
    "Header": "Header", "RegexpHeader": "Header",
    "AvoidStarImport": "Imports", "AvoidStaticImport": "Imports", "CustomImportOrder": "Imports", "IllegalImport": "Imports",
                   "ImportControl": "Imports", "ImportOrder": "Imports", "RedundantImport": "Imports", "UnusedImports": "Imports",
    "AtclauseOrder": "Javadoc Comments", "JavadocMethod": "Javadoc Comments", "JavadocPackage": "Javadoc Comments",
                   "JavadocParagraph": "Javadoc Comments", "JavadocStyle": "Javadoc Comments", "JavadocTagContinuationIndentation": "Javadoc Comments",
                   "JavadocType": "Javadoc Comments", "JavadocVariable": "Javadoc Comments", "NonEmptyAtclauseDescription": "Javadoc Comments",
                   "SingleLineJavadoc": "Javadoc Comments", "SummaryJavadoc": "Javadoc Comments", "WriteTag": "Javadoc Comments",
    "BooleanExpressionComplexity": "Metrics", "ClassDataAbstractionCoupling": "Metrics", "ClassFanOutComplexity": "Metrics",
                   "CyclomaticComplexity": "Metrics", "JavaNCSS": "Metrics", "NPathComplexity": "Metrics",
    "ArrayTypeStyle": "Miscellaneous", "AvoidEscapedUnicodeCharacters": "Miscellaneous", "CommentsIndentation": "Miscellaneous",
                   "DescendantToken": "Miscellaneous", "FileContentsHolder": "Miscellaneous", "FinalParameters": "Miscellaneous",
                   "Indentation": "Miscellaneous", "NewlineAtEndOfFile": "Miscellaneous", "OuterTypeFilename": "Miscellaneous",
                   "TodoComment": "Miscellaneous", "TrailingComment": "Miscellaneous", "Translation": "Miscellaneous",
                   "UncommentedMain": "Miscellaneous", "UniqueProperties": "Miscellaneous", "UpperEll": "Miscellaneous",
    "ModifierOrder": "Modifiers", "RedundantModifier": "Modifiers",
    "AbbreviationAsWordInName": "Naming Conventions", "AbstractClassName": "Naming Conventions", "CatchParameterName": "Naming Conventions",
                   "ClassTypeParameterName": "Naming Conventions", "ConstantName": "Naming Conventions",
                   "InterfaceTypeParameterName": "Naming Conventions", "LocalFinalVariableName": "Naming Conventions",
                   "LocalVariableName": "Naming Conventions", "MemberName": "Naming Conventions", "MethodName": "Naming Conventions",
                   "MethodTypeParameterName": "Naming Conventions", "PackageName": "Naming Conventions", "ParameterName": "Naming Conventions",
                   "StaticVariableName": "Naming Conventions", "TypeName": "Naming Conventions",
    "Regexp": "Regexp", "RegexpMultiline": "Regexp", "RegexpOnFilename": "Regexp", "RegexpSingleline": "Regexp", "RegexpSinglelineJava": "Regexp",
    "AnonInnerLength": "Size Violations", "ExecutableStatementCount": "Size Violations", "FileLength": "Size Violations",
                   "LineLength": "Size Violations", "MethodCount": "Size Violations", "MethodLength": "Size Violations",
                   "OuterTypeNumber": "Size Violations", "ParameterNumber": "Size Violations",
    "EmptyForInitializerPad": "WhiteSpaces", "EmptyForIteratorPad": "WhiteSpaces", "EmptyLineSeparator": "WhiteSpaces", "FileTabCharacter": "WhiteSpaces",
                   "GenericWhitespace": "WhiteSpaces", "MethodParamPad": "WhiteSpaces", "NoLineWrap": "WhiteSpaces", "NoWhitespaceAfter": "WhiteSpaces",
                   "NoWhitespaceBefore": "WhiteSpaces", "OperatorWrap": "WhiteSpaces", "ParenPad": "WhiteSpaces", "SeparatorWrap": "WhiteSpaces",
                   "SingleSpaceSeparator": "WhiteSpaces","TypecastParenPad": "WhiteSpaces", "WhitespaceAfter": "WhiteSpaces", "WhitespaceAround": "WhiteSpaces"
}

class MysqlConnection:
    def __init__(self, ip, user, password, database_name):
        self.db = MySQLdb.connect(ip, user, password, database_name, charset="utf8")
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


def insertTypes():
    JSON = '{"Annotations":["AnnotationLocation","AnnotationUseStyle","MissingDeprecated","MissingOverride","PackageAnnotation","SuppressWarnings","SuppressWarningsHolder"],"Block Checks":["AvoidNestedBlocks","EmptyBlock","EmptyCatchBlock","LeftCurly","NeedBraces","RightCurly"],"Class Design":["DesignForExtension","FinalClass","HideUtilityClassConstructor","InnerTypeLast","InterfaceIsType","MutableException","OneTopLevelClass","ThrowsCount","VisibilityModifier"],"Coding":["ArrayTrailingComma","AvoidInlineConditionals","CovariantEquals","DeclarationOrder","DefaultComesLast","EmptyStatement","EqualsAvoidNull","EqualsHashCode","ExplicitInitialization","FallThrough","FinalLocalVariable","HiddenField","IllegalCatch","IllegalInstantiation","IllegalThrows","IllegalToken","IllegalTokenText","IllegalType","InnerAssignment","MagicNumber","MissingCtor","MissingSwitchDefault","ModifiedControlVariable","MultipleStringLiterals","MultipleVariableDeclarations","NestedForDepth","NestedIfDepth","NestedTryDepth","NoClone","NoFinalizer","OneStatementPerLine","OverloadMethodsDeclarationOrder","PackageDeclaration","ParameterAssignment","RequireThis","ReturnCount","SimplifyBooleanExpression","SimplifyBooleanReturn","StringLiteralEquality","SuperClone","SuperFinalize","UnnecessaryParentheses","VariableDeclarationUsageDistance"],"Header":["Header","RegexpHeader"],"Imports":["AvoidStarImport","AvoidStaticImport","CustomImportOrder","IllegalImport","ImportControl","ImportOrder","RedundantImport","UnusedImports"],"Javadoc Comments":["AtclauseOrder","JavadocMethod","JavadocPackage","JavadocParagraph","JavadocStyle","JavadocTagContinuationIndentation","JavadocType","JavadocVariable","NonEmptyAtclauseDescription","SingleLineJavadoc","SummaryJavadoc","WriteTag"],"Metrics":["BooleanExpressionComplexity","ClassDataAbstractionCoupling","ClassFanOutComplexity","CyclomaticComplexity","JavaNCSS","NPathComplexity"],"Miscellaneous":["ArrayTypeStyle","AvoidEscapedUnicodeCharacters","CommentsIndentation","DescendantToken","FileContentsHolder","FinalParameters","Indentation","NewlineAtEndOfFile","OuterTypeFilename","TodoComment","TrailingComment","Translation","UncommentedMain","UniqueProperties","UpperEll"],"Modifiers":["ModifierOrder","RedundantModifier"],"Naming Conventions":["AbbreviationAsWordInName","AbstractClassName","CatchParameterName","ClassTypeParameterName","ConstantName","InterfaceTypeParameterName","LocalFinalVariableName","LocalVariableName","MemberName","MethodName","MethodTypeParameterName","PackageName","ParameterName","StaticVariableName","TypeName"],"Regexp":["Regexp","RegexpMultiline","RegexpOnFilename","RegexpSingleline","RegexpSinglelineJava  "],"Size Violations":["AnonInnerLength","ExecutableStatementCount","FileLength","LineLength","MethodCount","MethodLength","OuterTypeNumber","ParameterNumber"],"WhiteSpaces":["EmptyForInitializerPad","EmptyForIteratorPad","EmptyLineSeparator","FileTabCharacter","GenericWhitespace","MethodParamPad","NoLineWrap","NoWhitespaceAfter","NoWhitespaceBefore","OperatorWrap","ParenPad","SeparatorWrap","SingleSpaceSeparator","TypecastParenPad","WhitespaceAfter","WhitespaceAround"]}'

    mysql = MysqlConnection('localhost', 'root', '', 'code_analyze')
    # types = json.loads(JSON)
    for k, v in TREE.iteritems():
        for item in v:
            sql = 'INSERT INTO checkstyle_type (father_type, sub_type, internal_type, status) VALUES ("%s", "%s", "%s", "%d")' % (
            "Warn", item, k, 1)
            mysql.execute(sql)
    mysql.commit()
    mysql.close()

if __name__ == '__main__':
    # insertTypes()
    # if len(sys.argv) < 4:
    #     print 'Usage:\n\tpython %s __FILENAME__ check_id group_id' % sys.argv[0]
    #     exit(1)
    # checkstyle_file = open(sys.argv[1], 'r')
    group_id = 3
    check_id = 2
    checkstyle_file = open('2.checkstyle.result.0', 'r')
    # db = MySQLdb.connect('localhost', 'root', '', 'code_analyze', charset="utf8")
    # cursor = db.cursor()
    # for k, v in TREE.iteritems():
    #     sql_type_count = 'INSERT INTO checkstyle_stat_result (check_id, group_id, internal_type , count) VALUES ("%d", "%d", "%s", "%d")' % (check_id, group_id, k, random.randint(10, 50))
    #     cursor.execute(sql_type_count)
    #     for subtype in v:
    #         sql_subtype_count = 'INSERT INTO checkstyle_subtype_stat (check_id, group_id, sub_type , count) VALUES ("%d", "%d", "%s", "%d")' % (check_id, group_id, subtype, random.randint(0, 10))
    #         cursor.execute(sql_subtype_count)
    check_intetnaltype_stat = {"All": 0}
    check_subtype_stat = {}
    for k, v in TREE.iteritems():
        check_intetnaltype_stat[k] = 0
        for subtype in v:
            check_subtype_stat[subtype] = 0

    count = 0
    for line in checkstyle_file.xreadlines():
        line = line.strip()
        macthObj = re.match(REGEX, line)
        if macthObj is None:
            continue
        father_type = macthObj.group(1)
        sub_type = macthObj.group(3)
        # if sub_type in OMIT:
        #     continue

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
        # sql_result = 'INSERT INTO checkstyle_result \
        #       (father_type, sub_type, file, row, col, check_id, description, group_id) \
        #       VALUES ("%s", "%s", "%s", "%d", "%d", "%d", "%s","%d") ' % \
        #              (father_type, sub_type, file_name, row, column, check_id, description, group_id)
        # cursor.execute(sql_result)
        count += 1
        print father_type, sub_type, file_name, row, column, description

        internal_key = REVERSE_TREE[sub_type]
        check_intetnaltype_stat[internal_key] += 1
        check_intetnaltype_stat['All'] += 1
        check_subtype_stat[sub_type] += 1
    # print check_type_stat
    for k, v in check_subtype_stat.iteritems():
        print k, v
        # sql_type_count = 'INSERT INTO checkstyle_stat_result (check_id, group_id, internal_type , count) VALUES ("%d", "%d", "%s", "%d")' % (check_id, group_id, k, v)
        # cursor.execute(sql_type_count)

    # db.commit()
    # db.close()
