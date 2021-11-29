# coding=utf-8
from wsgiref.simple_server import make_server
from flask import Flask
from flask import request
import easyocr
import sys
import os
import io
import json

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

app = Flask(__name__)


@app.route("/easyOcr")
def easyOcr():
    path = request.args.get('path', '')
    if path != '':
        return getText(path)
    else:
        return '[]'
    
def getText(path):
    #args = sys.argv[1:]
    #if len(args) > 0 and args[0] != '':
    #    picPath = args[0]
    #    if not os.path.exists(picPath):
    #        raise Exception('File not exist')
    #    print(picPath)
    picPath = path
    reader = easyocr.Reader(['en','ch_sim'], False)
    res = reader.readtext(picPath)
    res = eval(res.__str__())
    return json.dumps(res)

if __name__ == '__main__':
    server = make_server('', 5000, app)
    server.serve_forever()
