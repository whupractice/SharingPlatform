import sys
import pymysql
import numpy as np
import pandas as pd

class Node():
    def __init__(self):
        self.next = {}
        self.isword = False #判断到当前结点是否已经是敏感词了
        self.words=""        #当前敏感词


class ac_automation(object):

    def __init__(self):
        self.root = Node()

    #将敏感词分解为由各个字组成的树
    def addKeys(self,words):
        temp_root = self.root   #当前根
        words = words.lower()
        for word in words:
            if not word in temp_root.next:
                temp_root.next[word] = Node()   #制造出新的结点
            temp_root = temp_root.next[word]
        temp_root.isword = True
        temp_root.words = words


    #搜索一段文本中的敏感词
    def search(self,text):
        temp_root = self.root
        result=[]
        for char in text:
            if char in temp_root.next:
                temp_root = temp_root.next[char]
            else:
                temp_root = self.root
            if temp_root.isword:
                result.append(temp_root.words)
                temp_root = self.root
        return result


    # 敏感词替换函数
    def words_replace(self, text):
        result = list(set(self.search(text)))
        for x in result:
            m = text.replace(x, '*' * len(x))
            text = m
        return text



    #读取百度敏感词库，将敏感词逐个加入字典树中
    def readKeys(self,t):
        for i in range(len(t)):
            self.addKeys(str(t['word'][i]))



if __name__ == '__main__':
    ac = ac_automation()
    conn = pymysql.connect(host='127.0.0.1:3306',
                       port=3306, user='root',
                       passwd='root', db='sharingdb', charset='utf8')
    t = pd.read_sql(sql="select word from senstive",con=conn)
    ac.readKeys(t)
    text = sys.argv[1]
    ans = ac.words_replace(text)
    print(ans)







