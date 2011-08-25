



def euclides(a,b):
    temp=0
    while (b!=0):
        temp=a
        a=b
        b=temp%b
    return a
    
print euclides(6,9)


for i in range(1,1000):
    for j in range(i,1000):
        if euclides(i,j)==1 and euclides(2**i-1,2**j-1)!=1:
            print i,j
