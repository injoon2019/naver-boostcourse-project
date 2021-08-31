document.addEventListener("DOMContentLoaded", function() {
    let submitButtonInitializer = new SubmitButtonInitializer();
    submitButtonInitializer.initSubmitButton();
});

function SubmitButtonInitializer() {
}

SubmitButtonInitializer.prototype = {
    initSubmitButton : function() {
        let submitButton = document.querySelector("#submit-button");
        submitButton.addEventListener("click", function(event) {
            event.preventDefault();
            let emailValidator = new EmailValidator();

            if (emailValidator.isValid()) {
                let submitForm = document.querySelector("#form1");
                submitForm.submit();
            }
        })
    }
}

function EmailValidator() {
}

EmailValidator.prototype = {
    isValid : function() {
        let emailField = document.querySelector("#resrv_id");
        let email = emailField.value;

        let emailValid = (/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i).test(email);

        if (!email || !emailValid) {
            emailField.value = ""
            emailField.placeholder = "이메일을 꼭 올바르게 입력해주세요";
            return false;
        }
        return true;
    }
}
