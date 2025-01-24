function selectMenuItem(menuItem, contentId) {
    // 移除所有菜单项目的选中状态
    var menuItems = document.querySelectorAll('.menu ul li');
    menuItems.forEach(function(item) {
        item.classList.remove('selected');
    });
    // 将当前点击的菜单项设置为选中状态
    menuItem.classList.add('selected');
    // 使用 AJAX 加载对应的内容
    var contentArea = document.getElementById('content-area');
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            contentArea.innerHTML = this.responseText;
        }
        if (contentId === 'issues') {
            // 调用 issues.js 中的初始化方法
            initIssuesPage();
        }
    };
    if (contentId === 'issues') {
        xhttp.open("GET", 'issues.html', true);
    } else {
        xhttp.open("GET", contentId, true);
    }
    xhttp.send();
}