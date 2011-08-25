#! /bin/python

import sys
import pdb
from math import *

def mean(lista):
	suma=0
	for x in lista:
		suma+=x
	return float(suma)/len(lista)

def entropy(alphabets, text):
	res = []
	dicts = []
	nsym=[]
	for i in range(alphabets):
		dicts.append({})
		nsym.append(0)
		res.append(0)
	#pdb.set_trace()
	for i in range(len(text)):
		nsym[i%alphabets]+=1
		if text[i] in dicts[i%alphabets]:
			dicts[i%alphabets][text[i]]+=1
		else:
			dicts[i%alphabets][text[i]]=1
	#pdb.set_trace()
	for i in range(alphabets):
		for c in dicts[i]:
			p=float(dicts[i][c])/float(nsym[i])
			res[i] += p*log(1/p)
	#pdb.set_trace()
	return res

stin = open("out.bin","r")
text=""
for s in stin:
	text+=s.strip()

for i in range(1,40):
	#pdb.set_trace()
	x = entropy(i,text)
	print i, mean(x)



