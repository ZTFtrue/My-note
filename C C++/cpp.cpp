#include <iostream>
#include <stdlib.h>

using namespace std;

int main(int argc, char *argv[])
{
    // 非EFI 返回256
    int a=system("dmesg | grep \"EFI v\"");
    return 0;
}