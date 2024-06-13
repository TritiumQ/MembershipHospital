window.onload = function(){
    //获取显示隐藏的按钮数组
    let showBtnArr = document.getElementsByClassName('fa-angle-down');
    //获取套餐明细数组
    let detailetBoxArr = document.getElementsByClassName('item-content');

    //设置所有套餐明细初始隐藏
    init();
    function init(){
        for(let i=0;i<detailetBoxArr.length;i++){
            detailetBoxArr[i].style.display = 'none';
        }
    }
    

    for(let i=0;i<showBtnArr.length;i++){
        showBtnArr[i].onclick = function(){
            if(detailetBoxArr[i].style.display=='none'){
                init();
                detailetBoxArr[i].style.display='block';
            }else{
                detailetBoxArr[i].style.display='none';
            }
        }
    }
}