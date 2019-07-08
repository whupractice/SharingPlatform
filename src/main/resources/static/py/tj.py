#推荐算法


import pymysql
import numpy as np
import pandas as pd
from math import *
import sys

conn = pymysql.connect(host='39.96.88.130',
                       port=3306, user='xsy', 
                       passwd='123', db='sharingdb', charset='utf8')
t = pd.read_sql(sql="select lesson_id, phone, star from sl",con=conn)
data = {} #保存每位用户上的课程和评价的星数

for i in range(len(t)):
    if not t['phone'][i] in data.keys():
        data[t['phone'][i]] = {t['lesson_id'][i] : t['star'][i]}
    else:
        data[t['phone'][i]][t['lesson_id'][i]] = t['star'][i]

        
        
#计算两个用户的相似度
def cal_similarity(user1,user2):
    user1_data = data[user1]
    user2_data = data[user2]
    distance = 0
    #计算两个用户之间的欧氏距离
    for key in user1_data.keys():
        if key in user2_data.keys():
            distance+=pow(float(user1_data[key])-float(user2_data[key]),2)
    # print(distance)
    return 1./(1.+sqrt(distance)) #返回值越大，相似度越大
    
#获取与一个用户最相似的4个用户
def getTop4_simailar(user):
    res=[]
    for userId in data.keys():
        if not userId == user:
            similar = cal_similarity(user,userId)
            res.append((userId,similar))
    res.sort(key=lambda val:val[1],reverse=True)
    return res[:4]


#推荐课程给用户
def recommend(user):
    top4_similar = getTop4_simailar(user)
    top_user = top4_similar[0][0]#获取最相似的用户
    recommend_movie = []
    movies_ratings = data[top_user]
    for movie in movies_ratings.keys():
        if movie not in data[user].keys():  #挑选出最相似用户的电影中，用户未看过的电影
            recommend_movie.append((movie,movies_ratings[movie]))
    recommend_movie.sort(key = lambda val:val[1],reverse=True)#按评分排序
    return recommend_movie[:10]
    
if __name__ == '__main__':
    recommend_lesson = recommend(int(sys.argv[1]))
    for i in range(len(recommend_lesson)):
        print(recommend_lesson[i][0])