import numpy as np
import matplotlib.pyplot as plt
import pymysql
import pandas as pd
import sys

conn = pymysql.connect(host='39.96.88.130',
                       port=3306, user='xsy', 
                       passwd='123', db='sharingdb', charset='utf8')
cursor = conn.cursor()
cursor.execute(
    'select lesson_name, count(lesson_id) from sl_view group by lesson_id')
res = cursor.fetchall()
lessons = []
values = []
for i in range(len(res)):
    lessons.append(res[i][0])
    values.append(res[i][1])

length = int(sys.argv[1]) if int(sys.argv[1])<len(res) else len(res)

for i in range(len(res)-1):
    for j in range(len(res) - i - 1):
        if values[j] >values[j+1]:
            values[j],values[j+1] = values[j+1],values[j]
            lessons[j],lessons[j+1] = lessons[j+1],lessons[j]

lessons = lessons[-length:]
values = values[-length:]
    
# 中文乱码的处理
plt.rcParams['font.sans-serif'] =['SimHei']
plt.rcParams['axes.unicode_minus'] = False

# 绘图
plt.barh(range(length), values, 0.4,color='r', alpha = 1)
# 添加轴标签
plt.ylabel('课程')
# 添加标题
plt.title('课程热度榜top')
# 添加刻度标签
plt.yticks(range(len(lessons)),lessons)
# 设置Y轴的刻度范围
plt.xlim([0,10])

# 为每个条形图添加数值标签
for x,y in enumerate(values):
    plt.text(y+0.2,x,'%s' %y,va='center')
plt.figure(facecolor='#a9f4de24',edgecolor='black')
# 显示图形
plt.savefig('target/classes/static/img/lessonRank.jpg')
    