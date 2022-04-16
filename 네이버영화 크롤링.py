#!/usr/bin/env python
# coding: utf-8

# # 네이버 영화 크롤링

# 제목

# In[2]:


import pandas as pd
from urllib.request import urlopen
from bs4 import BeautifulSoup
from tqdm.notebook import tqdm
import time

link_list = []
name_list = []

for i in range(1,2):
    url = "https://movie.naver.com/movie/sdb/browsing/bmovie.naver?nation=KR&page="+str(i)
    html = urlopen(url)
    
    soup = BeautifulSoup(html.read(), "html.parser")
    
    time.sleep(0.5)
    
    links  = soup.select('#old_content > ul > li > a')
    
    for link in links:
        
        names = []
        names.append(link.get_text())
        link_list.append(link.attrs["href"])
        name_list.append(names)

#data={'name_list':name_list}
df=pd.DataFrame(name_list)
df['movie_name']=df[0].map(str)
df_new=df.drop([0],axis=1)
df_new


# 감독

# In[4]:


directors = []

for i in link_list:
    director_list = []
    
    url = "https://movie.naver.com" + i
    html = urlopen(url)  
    
    soup = BeautifulSoup(html.read(), "html.parser")
    
    time.sleep(0.5)
    
    
    a = soup.select('#content > div.article > div.mv_info_area > div.mv_info > dl > dd:nth-of-type(2) > p > a')
    
    for i in a:
        director_list.append(i.get_text())

    directors.append(director_list)
    
df1=pd.DataFrame(directors)
df1['director']=df1[0].map(str)+","+df1[1].map(str)

t=len(df1.columns)
a=[]

for i in range(t-1):
    a.append(i)

df1_new=df1.drop(a,axis=1)
df1_new


# 개봉일

# In[5]:


dates = []

for i in link_list:
    date_list = []
    url = "https://movie.naver.com" + i
    html = urlopen(url)  
    
    soup = BeautifulSoup(html.read(), "html.parser")
    
    time.sleep(0.5)
    
    a = soup.select('#content > div.article > div.mv_info_area > div.mv_info > dl > dd:nth-of-type(1) > p > span:nth-of-type(4) > a')
    
    for i in a:
        date_list.append(i.get_text())
               
    dates.append(date_list)
    

df2=pd.DataFrame(dates)
df2['dates']=df2[0].map(str)+df2[1].map(str)

t=len(df2.columns)
a=[]

for i in range(t-1):
    a.append(i)
    
df2_new=df2.drop(a,axis=1)
df2_new


# 등급

# In[ ]:


grade = []

for i in link_list:
    grade_list = []
    url = "https://movie.naver.com" + i
    html = urlopen(url)  
    
    soup = BeautifulSoup(html.read(), "html.parser")
    
    time.sleep(0.5)
    
    titles=soup.select('#content > div.article > div.mv_info_area > div.mv_info > dl > dd > p > a')
    for title in titles:
        if ('grade' in title['href']):
            grade_list.append(title.get_text())
    grade.append(grade_list)
    

df3=pd.DataFrame(grade)
df3['grade']=df3[0].map(str)
df3_new=df3.drop([0],axis=1)
df3_new


# 장르

# In[ ]:


genre = []

for i in link_list:
    genre_list = []
    url = "https://movie.naver.com" + i
    html = urlopen(url)  
    
    soup = BeautifulSoup(html.read(), "html.parser")
    
    time.sleep(0.5)
    
    
    titles=soup.select('#content > div.article > div.mv_info_area > div.mv_info > dl > dd > p > span > a')
    for title in titles:
        if ('genre' in title['href']):
            genre_list.append(title.get_text())
    genre.append(genre_list)
    
df4=pd.DataFrame(genre)
df4['genre']=df4[0].map(str)+","+df4[1].map(str)+","+df4[2].map(str)

t=len(df4.columns)
a=[]

for i in range(t-1):
    a.append(i)
    
df4_new=df4.drop(a,axis=1)
df4_new


# 배우

# In[ ]:


actors=[]

for i in link_list:
    actor_list = []
    new_i = i.replace("basic",'detail')
    url = "https://movie.naver.com" + new_i

    html = urlopen(url)  
    
    soup = BeautifulSoup(html.read(), "html.parser")
    
    time.sleep(0.5)
    
    result_sec=soup.select('#content > div.article > div.section_group.section_group_frst > div.obj_section.noline > div > div.lst_people_area.height100 > ul > li > div > a')
    
    for i in result_sec:
        actor_list.append(i.get_text())
    actors.append(actor_list)

df5=pd.DataFrame(actors)

df5['actors']=df5[0].map(str)+","+df5[1].map(str)+","+df5[2].map(str)+","+df5[3].map(str)+","+df5[4].map(str)+","+df5[5].map(str)+","+df5[6].map(str)+","+df5[7].map(str)+","+df5[8].map(str)+","+df5[9].map(str)

t=len(df5.columns)
a=[]

for i in range(t-1):
    a.append(i)
    
df5_new=df5.drop(a,axis=1)
df5_new


# 줄거리

# In[ ]:


story = []

for i in link_list:
    s = []
    url = "https://movie.naver.com" + i
    html = urlopen(url)  
    
    soup = BeautifulSoup(html.read(), "html.parser")
    
    time.sleep(0.5)
    
    titles = soup.select('#content > div.article > div.section_group.section_group_frst > div:nth-of-type(1) > div > div.story_area > p')
    for title in titles:
        s.append(title.get_text(strip=True))
    story.append(s)
    
from wordcloud import WordCloud
from collections import Counter
from konlpy.tag import Komoran
komoran=Komoran()

key_words=[]

dff=pd.DataFrame(story)
dff['story']=dff[0].map(str)
dff_new=dff.drop([0],axis=1)

for i in dff.story.tolist():
    words=[]
    i_noun3=komoran.nouns(i)
    count3=Counter(i_noun3)
    #print(count3.most_common(5))
    for i in count3.most_common(5):
        words.append(i[0])
    key_words.append(words)

df6=pd.DataFrame(key_words)
df6['key_words']=df6[0].map(str)+","+df6[1].map(str)+","+df6[2].map(str)+","+df6[3].map(str)+","+df6[4].map(str)
df6_new=df6.drop([0,1,2,3,4],axis=1)
df6_new


# In[ ]:


# result = pd.concat([df_new,df1_new,df2_new,df3_new,df4_new,df5_new,df6_new],axis=1)

# result.to_csv('1101-1150.csv',index=False,encoding="utf-8") #엑셀 변환


# In[ ]:




