#!/usr/bin/env python
import sys
f=open("/dev/null", 'w')
#sys.stdout=f
#sys.stderr=f
sys.path.insert(0, "/usr/local/Cellar/python3/3.6.4_2/Frameworks/Python.framework/Versions/3.6/lib/python3.6/site-packages")


import numpy as np
import keras
#import pandas as pd
import tensorflow as tf
#import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import os
from PIL import Image



import keras
import h5py
#from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D
from keras import backend as K
from keras.models import load_model




os.environ['TF_CPP_MIN_LOG_LEVEL'] = '1'


cnn = load_model("final_cnn.h5")


print(3)

def find_max(array):
	return list(array).index(max(array))

def downsize(picture_path):
    img       = Image.open(picture_path)
    numpy_img = mpimg.imread(picture_path) # or np.array(img)
    img       = img.resize((48, 64), Image.ANTIALIAS)
    
    return np.array(img)

def f(conv_nn, x):
    return find_max(conv_nn.predict(np.array([x]))[0])

print(f(cnn, downsize("Desktop/pic.jpg")))