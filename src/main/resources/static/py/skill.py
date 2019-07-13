import numpy as np
import matplotlib.pyplot as plt
import pymysql
import pandas as pd
import sys

conn = pymysql.connect(host='127.0.0.1:3306',
                       port=3306, user='root',
                       passwd='root', db='sharingdb', charset='utf8')
cursor = conn.cursor()
cursor.execute(
    'select subject from sl_view where phone = %s',sys.argv[1])
res = cursor.fetchall()
subjects = ['哲学','经济学','法学','教育学','文学','历史学','理学','工学','农学','医学','管理学','艺术学','体育']
values = [0,0,0,0,0,0,0,0,0,0,0,0,0]
for i in range(len(res)):
    for j in range(len(subjects)):
        if res[i][0] == subjects[j]:
            values[j] = values[j]+1
#标签
labels = np.array(subjects)
#数据个数
dataLenth = 13
#数据
data = np.array(values)

angles = np.linspace(0, 2*np.pi, dataLenth, endpoint=False)
data = np.concatenate((data, [data[0]])) # 闭合
angles = np.concatenate((angles, [angles[0]])) # 闭合

fig = plt.figure()
ax = fig.add_subplot(111, polar=True)# polar参数！！
ax.plot(angles, data, 'bo-', linewidth=2)# 画线
ax.fill(angles, data, facecolor='b', alpha=0.25)# 填充
ax.set_thetagrids(angles * 180/np.pi, labels, fontproperties="SimHei")
ax.set_title("", va='bottom', fontproperties="SimHei")
ax.set_rlim(0,max(values)+2)
ax.grid(True)
ax.set_yticks([])
plt.savefig('C:/Users/Administrator.000/Desktop/File/img/skill/'+sys.argv[1]+'.jpg',transparent=True)