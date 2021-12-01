# coding=utf-8
from wsgiref.simple_server import make_server
from flask import Flask
from flask import request
import paddlehub as hub
import cv2
import json

ocr = hub.Module(name="chinese_ocr_db_crnn_server")

app = Flask(__name__)
   
@app.route("/paddle-ocr")
def paddleOcrR():
    path = request.args.get('path', '')
    if path != '':
        return getPaddleText(path)
    else:
        return '[]'

def getPaddleText(path):
    res = ocr.recognize_text(images=[cv2.imread(path)])
    return json.dumps(res)

@app.errorhandler(Exception)
def exceptionHandler(e):
    print(e)
    return 'error'

if __name__ == '__main__':
    server = make_server('', 5001, app)
    server.serve_forever()
