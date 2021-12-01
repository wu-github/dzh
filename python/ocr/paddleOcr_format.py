# coding=utf-8
import paddlehub as hub
import cv2
import sys
import io
import json

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

def getText():
    picPath = sys.argv[1]
    ocr = hub.Module(name="chinese_ocr_db_crnn_server")
    res = ocr.recognize_text(images=[cv2.imread(picPath)])
    return json.dumps(res)

if __name__ == '__main__':
    result = getText()
    print(result)

