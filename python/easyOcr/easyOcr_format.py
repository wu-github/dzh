# coding=utf-8
import easyocr
import sys
import os
import io
import json

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

def getText():
    #args = sys.argv[1:]
    #if len(args) > 0 and args[0] != '':
    #    picPath = args[0]
    #    if not os.path.exists(picPath):
    #        raise Exception('File not exist')
    #    print(picPath)
    picPath = sys.argv[1]
    reader = easyocr.Reader(['en','ch_sim'], False)
    res = reader.readtext(picPath)
    res = eval(res.__str__())
    return json.dumps(res)

if __name__ == '__main__':
    result = getText()
    print(result)