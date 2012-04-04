

import random

test_cases=10
lower_bound=2**32
upper_bound=2**1024
ops = ['+','-','*','/','%','^']

for i in range(0,test_cases):
    opix = random.randint(0,len(ops)-1)
    op = ops[opix]
    print op,
    if op=='^':
        a=random.randint(lower_bound,upper_bound)
        b=random.randint(lower_bound,upper_bound)
        c=random.randint(lower_bound,upper_bound)
        print a,b,c
    elif op=='/' or op=='%':
        a=random.randint(lower_bound,upper_bound**2)
        b=random.randint(lower_bound,upper_bound)
        print a,b
    else:
        a=random.randint(lower_bound,upper_bound)
        b=random.randint(lower_bound,upper_bound)
        print a,b


