#!/bin/bash
# nhkim
set fileencoding=utf-8

echo -e "CocoaPod 메뉴선택 >> 
1: Pod 설치       
2: Pod 제거(초기화)
3: XCode DerivedData 삭제     
4: Pod 업데이트"

read Input

if [ "${Input}" -eq 1 ];then
    echo Pod 설치
    pod install
elif [ "${Input}" -eq 2 ];then
    echo Pod 제거 초기화 
    pod deintegrate
elif [ "${Input}" -eq 3 ];then
    echo XCode DerivedData 삭제
    rm -rf ~/Library/Developer/Xcode/DerivedData/*
elif [ "${Input}" -eq 4 ];then
    echo  Pod 업데이트
    pod update
else
    echo 잘못 입력

fi


