import numpy as np
import matplotlib.pyplot as plt
import pymysql
import pandas as pd

conn = pymysql.connect(host='39.96.88.130',
                       port=3306, user='xsy', 
                       passwd='123', db='sharingdb', charset='utf8')
cursor = conn.cursor()
cursor.execute(
    'select distinct subject, count(lesson_id) from lesson group by subject')
res = cursor.fetchall()

subjects = []
values = []
for i in range(len(res)):
    subjects.append(res[i][0])
    values.append(res[i][1])


# 中文乱码和坐标轴负号的处理
plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False

# 将横、纵坐标轴标准化处理，保证饼图是一个正圆，否则为椭圆
plt.axes(aspect='equal')

# 控制x轴和y轴的范围
plt.xlim(-9,9)
plt.ylim(-9,9)

# 绘制饼图
plt.pie(x = values,# 绘图数据
    labels=subjects, # 添加水果销量水平标签
    autopct='%.1f%%', # 设置百分比的格式，这里保留一位小数
    pctdistance=0.8,# 设置百分比标签与圆心的距离
    labeldistance = 1.15, # 设置销量水平标签与圆心的距离
    startangle = 180, # 设置饼图的初始角度
    radius = 6, # 设置饼图的半径
    counterclock = False, # 是否逆时针，这里设置为顺时针方向
    wedgeprops = {'linewidth': 1.5, 'edgecolor':'green'},# 设置饼图内外边界的属性值
    textprops = {'fontsize':12, 'color':'k'}, # 设置文本标签的属性值
    center = (0,0), # 设置饼图的原点
    frame = 1)# 是否显示饼图的图框，这里设置显示
plt.figure(facecolor='#a9f4de24',edgecolor='black')
# 删除x轴和y轴的刻度
plt.xticks(())
plt.yticks(())
# 添加图标题
plt.title('各学科课程总数统计')

# 显示图形
plt.savefig('target/classes/static/img/subject.jpg')