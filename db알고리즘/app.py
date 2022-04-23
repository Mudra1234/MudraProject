# app.py
# -*- coding: utf-8 -*-
from flask import Flask, render_template, request, jsonify
import Algo

# flask 객체 인스턴스 생성
app = Flask(__name__)

@app.route('/')   # 접속하는 url
def index():
    return 'Safety Login'

answers = []


@app.route('/getAnswer', methods = ['POST'])
def getAnswer():
    answer = request.get_json()     # json 받아오기
    print(answer)
    # 여기서 py 돌린 최종 영화 데이터를 return 해주면 될듯
    result = Algo.runningApp(answer)
    return jsonify(result)      # 받아온 데이터 전송



@app.route('/userLogin', methods = ['POST'])            # test용 루트
def userLogin():
    user = request.get_json()#json 데이터를 받아옴
    print(user)
    return jsonify(user)# 받아온 데이터를 다시 전송



@app.errorhandler(404)
def page_not_found(error):
    app.logger.error(error)
    return render_template('page_not_found.html'), 404



    