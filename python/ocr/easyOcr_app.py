# coding=utf-8
from wsgiref.simple_server import make_server
from flask import Flask
from flask import request
import easyocr
import json
#import sys
#import os
#import io

#sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
#sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

reader = easyocr.Reader(['en','ch_sim'], False)

app = Flask(__name__)

@app.route("/easy-ocr")
def easyOcrR():
    path = request.args.get('path', '')
    if path != '':
        return getEasyText(path)
    else:
        return '[]'

def getEasyText(path):
    #args = sys.argv[1:]
    #if len(args) > 0 and args[0] != '':
    #    picPath = args[0]
    #    if not os.path.exists(picPath):
    #        raise Exception('File not exist')
    #    print(picPath)
    picPath = path
    res = reader.readtext(picPath)
    res = eval(res.__str__())
    return json.dumps(res)

@app.errorhandler(Exception)
def exceptionHandler(e):
    print(e)
    return 'error'

if __name__ == '__main__':
    server = make_server('', 5000, app)
    server.serve_forever()
