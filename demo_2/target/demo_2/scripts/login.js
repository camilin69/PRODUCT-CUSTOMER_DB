function login(){
    const email = document.getElementById('reg_email').value;
    const password = document.getElementById('reg_password').value;


    if (!email || !password) {
        alert('FIELDS UNFILLED');
        return;
    }else{
        window.location.href = '../index/customer.html';
    }



}