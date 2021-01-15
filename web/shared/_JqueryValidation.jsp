<script src="${pageContext.request.contextPath}/node_modules/jquery-validation/dist/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/node_modules/jquery-validation-unobtrusive/dist/jquery.validate.unobtrusive.min.js"></script>
<script>
    $.validator.addMethod("phoneVN", function (phone_number, element) {
        phone_number = phone_number.replace(/\s+/g, "");
        return this.optional(element) || phone_number.length > 9 &&
            phone_number.match(/^((0[3|5|7|8|9])+([0-9]{8}))|^\+?((84|84[3|5|7|8|9])+([0-9]{9}))\b$/);
    }, "Please specify a valid phone number");

    $.validator.addMethod("intNumber", function (number, element) {
        number = number.replace(/\s+/g, "");
        return this.optional(element) || number.match(/^[\d]+\b$/);
    }, "Please input a valid positive integer number")
</script>