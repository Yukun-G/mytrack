// 定义初始化方法
function initIssuesPage() {
    // console.log('initIssuesPage():============');
    const searchButton = document.getElementById('searchButton');
    const resetButton = document.getElementById('resetButton');
    if (searchButton) {
        searchButton.addEventListener('click', function () {
            const status = document.getElementById('status').value;
            const title = document.getElementById('title').value;
            const createdBy = document.getElementById('createdBy').value;
            const assignedTo = document.getElementById('assignedTo').value;

            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    document.querySelector('table tbody').innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "/project/issues?status=" + status + "&title=" + title + "&createdBy=" + createdBy + "&assignedTo=" + assignedTo, true);
            xhttp.send();
        });
    }
    if (resetButton) {
        // 为重置按钮添加点击事件监听器
        resetButton.addEventListener('click', function () {
            // 将状态选择框的值设为 1
            document.getElementById('status').value = '1';
            // 将标题输入框的值清空
            document.getElementById('title').value = '';
            // 将创建人输入框的值清空
            document.getElementById('createdBy').value = '';
            // 将指派人输入框的值清空
            document.getElementById('assignedTo').value = '';
        });
    }
}