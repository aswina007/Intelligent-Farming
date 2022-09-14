import os
import pickle
import pandas as pd
from sklearn.ensemble import IsolationForest

path ='dataset'
for i in os.listdir(path): 		# for reading eaach files in the folder 'path'
	# print(i)
	data=pd.read_csv(path+'/'+i)# reading each csv files and stores each entry as data frames.(pandas)
	# print(data.head(5)) 		#Displaying top 5 data frames
	data.dropna(inplace=True) 	# for dropping rows or columns with null values(pandas)
								# inplace = true means the changes are to made on the same copy
	# print(data.head(5))
	X = data[['season','pH','soil_type','elevation','temperature']]
	# print(X.head(5))
	model = IsolationForest(n_estimators=100,contamination= 0.0,random_state=42)
	model.fit(X)
	name = i.split('.')

	pickle.dump(model, open('models/model_'+name[0]+'.sav', 'wb'))		# wb is writing in binary mode


# Python pickle module is used for serializing and de-serializing a Python object structure.
# Any object in Python can be pickled so that it can be saved on disk.
#  What pickle does is that it “serializes” the object first before writing it to file.
# Pickling is a way to convert a python object (list, dict, etc.) into a character stream.
# The idea is that this character stream contains all the information necessary to reconstruct the object in another python script.

