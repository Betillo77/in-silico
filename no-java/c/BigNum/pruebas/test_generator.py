

import random

test_cases=100
lower_bound=2**32
upper_bound=2**128


for i in range(0,test_cases):
    op=random.choice(['*','+','-','/'])
    a=random.randint(lower_bound,upper_bound)
    b=random.randint(lower_bound,upper_bound)
    print op,a,b


