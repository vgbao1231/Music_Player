const $ = document.querySelector.bind(document)
const $$ = document.querySelectorAll.bind(document)
function togglePassword() {
    const passwordField = document.querySelector("#password");
    if (passwordField.type === "password") {
        passwordField.type = "text";
        document.querySelector(".fa-eye").style.display="none";
        document.querySelector(".fa-eye-slash").style.display="inline";
    } else {
        passwordField.type = "password";
        document.querySelector(".fa-eye").style.display="inline";
        document.querySelector(".fa-eye-slash").style.display="none";
    }
}

// Thống báo toast
const toastContainer = $("#toast")
if (toastContainer){
    toast({
        message: toastContainer.dataset.message,
        type: toastContainer.dataset.type
    })
}
function toast({ message, type }) {
    const icons = {
        success: '<i class="fa-solid fa-circle-check"></i>',
        error: '<i class="fa-solid fa-circle-exclamation"></i>'
    }
    if (toastContainer) {
        const toast = document.createElement("div")
        toast.classList.add('toast', `toast__${type}`, 'center')
        toast.innerHTML = `
            <div class="toast__icon">
                ${icons[type]}
            </div>
            <div class="toast__body">
                <h3 class="toast__title">${type === "success" ? "Thành công" : "Thất bại"}</h3>
                <p class="toast__message">${message}</p>
            </div>
            <div class="toast__close">
                <i class="fa-solid fa-xmark" style="color: gray;"></i>
            </div>
        `
        toastContainer.appendChild(toast)
        // Auto close
        const autoCloseId = setTimeout(function () {
            toastContainer.removeChild(toast)
        }, 30000)
        // Close when click
        toast.onclick = function () {
            // toast.style.opacity = 0
            setTimeout(function () {
                toastContainer.removeChild(toast)
            }, 300)
            clearTimeout(autoCloseId)
        }
    }
}

//Tùy chỉnh otp
const otp = document.querySelectorAll('.otp_field');
otp[0].focus()
otp.forEach((field, index) => {
    field.addEventListener('keydown', (e) => {
        if (e.key >= '0' && e.key <= '9') {
            otp[index].value = "";
            setTimeout(() => {
                if (otp[index + 1]) otp[index + 1].focus();
            }, 0);
        } else if (e.key === 'Backspace' && otp[index].value === 0) {
            setTimeout(() => {
                if (otp[index - 1]) otp[index - 1].focus();
            }, 0);
        } else if (e.key === 'ArrowLeft') {
            setTimeout(() => {
                if (otp[index - 1]) otp[index - 1].focus();
            }, 0);
        } else if (e.key === 'ArrowRight') {
            setTimeout(() => {
                if (otp[index + 1]) otp[index + 1].focus();
            }, 0);
        }
    })
})
let otpValue = ''
$('.verify-form form').addEventListener('submit',() => {
    otp.forEach(input => {
        otpValue += input.value;
    });
    const submitValue = $('input[name="otp"]')
    submitValue.value = otpValue
    console.log(submitValue)
    console.log(submitValue.value)
})

