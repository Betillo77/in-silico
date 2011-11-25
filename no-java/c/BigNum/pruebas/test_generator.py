

import random

test_cases=100
lower_bound=2**32
upper_bound=2**1024


for i in range(0,test_cases):
    op=random.choice(['*','+','-','/','%'])
    #op=random.choice(['+','-','*'])
    a=random.randint(lower_bound,upper_bound)
    b=random.randint(lower_bound,2**512)
    print op,a,b


