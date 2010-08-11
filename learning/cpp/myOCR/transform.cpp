
#include "transform.h"
#include <cstdio>


#define min(X,Y) ( ((X)<(Y)) ? (X) : (Y) )
#define max(X,Y) ( ((X)>(Y)) ? (X) : (Y) )

using namespace MyOCR;

Transform::Transform() {
    cvtWeight[0]=0.3f; cvtWeight[1]=0.59f; cvtWeight[2]=0.11f; cvtWeight[3]=0;
    wsize=40; kfac=0.3;
}

void Transform::toGrayScale(Matrix* res, Matrix* m) {
    assert(res != 0); assert(m != 0);
    int N = m->getWidth()*m->getHeight(), K=m->getChannels();
    float ans;
    assert(K >= 3); assert(res->getChannels() == 1);
    assert(N <= res->getWidth()*res->getHeight());
    pixel *dres = res->getData(), *dm = m->getData();
    for (int i=0; i<N; i++) {
        ans=0;
        for (int k=0; k<K; k++) {
            ans += (*dm)*cvtWeight[k];
            dm++;
        }
        *dres = (pixel)ans;
        dres++;
    }
}

void Transform::binarize(Matrix* res, Matrix* m) {
    int w = this->wsize;
    float k = this->kfac;
    assert(k >= 0.001 && k<=0.999);
    assert(w>0 && k<1000);

    int xmin,ymin,xmax,ymax,area;
    int whalf = w >> 1;
    int im_w = m->getWidth(), im_h = m->getHeight();    
    int64 sum[im_h][im_w], sumsq[im_h][im_w];
    int64 row, rowsq;
    double mean, stddev, num, snum, thresh;
    pixel *img = m->getData(), *ires;

    row=0; rowsq=0;
    for (int i=0; i<im_h; i++) {
        row += (*img); rowsq += (*img)*(*img);
        sum[i][0]=row;
        sumsq[i][0]=rowsq;
        img += im_w*sizeof(pixel);
    }
    img = m->getData(); img++;
    for (int j=1; j<im_w; j++) {
        sum[0][j] = sum[0][j-1] + (*img);
        sumsq[0][j] = sumsq[0][j-1] + (*img)*(*img);
        img++;
    }
    for (int i=1; i<im_h; i++) {
        row=(*img); rowsq=row*row; img++;
        for (int j=1; j<im_w; j++,img++) {
            row += (*img); rowsq += (*img)*(*img);
            sum[i][j] = sum[i-1][j] + row;
            sumsq[i][j] = sumsq[i-1][j] + rowsq;
        }
    }

    /*for (int i=0; i<5; i++) {
        for (int j=0; j<5; j++) {
            printf("%d,%d,%d\t",m->get(i,j),sum[i][j],sumsq[i][j]);
        }
        printf("\n");
    }*/

    img = m->getData(); ires = res->getData();
    for (int i=0; i<im_h; i++) {
        for (int j=0; j<im_w; j++) {
            xmin = max(0,j-whalf);
            ymin = max(0,i-whalf);
            xmax = min(im_w-1,j+whalf);
            ymax = min(im_h-1,i+whalf);
            area = (xmax-xmin+1)*(ymax-ymin+1);

            if (xmin==0 && ymin==0) {
                num = sum[ymax][xmax];
                snum = sumsq[ymax][xmax];
            } else if (xmin==0 && ymin>0) {
                num = sum[ymax][xmax] - sum[ymin-1][xmax];
                snum = sumsq[ymax][xmax] - sumsq[ymin-1][xmax];
            } else if (xmin>0 && ymin==0) {
                num = sum[ymax][xmax] - sum[ymax][xmin-1];
                snum = sumsq[ymax][xmax] - sumsq[ymax][xmin-1];
            } else {
                num = sum[ymax][xmax] + sum[ymin-1][xmin-1];
                num -= sum[ymax][xmin-1] + sum[ymin-1][xmax];
                snum = sumsq[ymax][xmax] + sumsq[ymin-1][xmin-1];
                snum -= sumsq[ymax][xmin-1] + sumsq[ymin-1][xmax] ;
            }
            mean = num/area;
            num = snum - (num*num/area);
            assert(num >= 0);
            stddev = sqrt( num / (area-1) );
            thresh = mean*(1+k*((stddev/128)-1));
            if (*img < thresh)
                *ires = 0;
            else
                *ires = 255;
            img++; ires++;
        }
    }
}
