$(function () {
    $(document).scroll(function () {
        const $nav = $(".navbar");
        if ($(this).scrollTop() > $nav.height() / 2) {
            $nav.addClass("sticky-top")
            $nav.removeClass("bg-dark")
            $nav.addClass("bg-light")
            $nav.removeClass("navbar-dark")
            $nav.addClass("navbar-light")
        } else {
            $nav.removeClass("sticky-top")
            $nav.removeClass("bg-light")
            $nav.addClass("bg-dark")
            $nav.removeClass("navbar-light")
            $nav.addClass("navbar-dark")
        }
    })
})


$(document).ready(function () {
    $('.carousel-next').click(function () {
        const target = $(this).data('carousel-target')
        const $carouselText = $('#carousel-text-' + target)
        let page = $carouselText.data('page')
        const count = $carouselText.data('count')
        if (page < count) {
            page++;
            $carouselText.text(page + '/' + count)
            $carouselText.data('page', page)
        }
    })

    $('.carousel-prev').click(function () {
        const target = $(this).data('carousel-target')
        const $carouselText = $('#carousel-text-' + target)
        let page = $carouselText.data('page')
        const count = $carouselText.data('count')
        if (page > 1) {
            page--;
            $carouselText.text(page + '/' + count)
            $carouselText.data('page', page)
        }
    })
})


$(document).ready(function () {
    $('.add-button').click(function () {
        const productId = $(this).data('product-id')
        $.get("DispatcherServlet?action=add-to-cart&productId=" + productId, function (data, status) {
            if (status === 'success') {
                toastr.success('Thanh cong')
            }
        }).fail(function (jqXHR, status, errorThrown) {
            if (jqXHR.status === 404) {
                toastr.error('Không thành công, vui long tải lại trang :( sorry')
            } else if (jqXHR.status === 401) {
                window.location.href = "DispatcherServlet?action=login";
            }
        })
    })
})
