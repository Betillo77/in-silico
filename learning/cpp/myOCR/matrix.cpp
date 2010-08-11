
#include "matrix.h"

using namespace MyOCR;

Matrix::Matrix(int width, int height, int channels) {
    this->width = width;
    this->height = height;
    this->channels = channels;
    this->data = new pixel[width*height*channels];
}

Matrix::~Matrix() {
    delete [] data;
}

int Matrix::getChannels() {
    return this->channels;
}

pixel* Matrix::getData() {
    return this->data;
}

int Matrix::getHeight() {
    return this->height;
}

int Matrix::getWidth() {
    return this->width;
}

pixel Matrix::get(int i, int j) {
    return this->data[i*width + j];
}