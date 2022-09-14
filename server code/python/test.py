import os
import pickle
import pandas as pd

data=pd.read_csv('test.csv')

#out=''
path='models'
crop={"carrot":0,"cucumber":0,"pepper":0 ,"tapioca": 0}
for i in os.listdir(path):

	model=pickle.load(open(path+'/'+i, 'rb'))
	pred=model.predict(data)
	res=model.decision_function(data)

	label=i.split('_')
	label=label[1].split('.')
	for p in pred:
		if p == -1 or res <= 0.01:
			pass
		else:
			crop[label[0]] = res
			#out = out+label[0]+'#'
#print(out)
#print(crop)
for key, value in sorted(crop.items(), key=None,reverse = True):
	if(value != 0):
		print("%s#" % (key),end="")











