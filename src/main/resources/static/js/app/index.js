/*
* var main 이라는 변수의 속성으로 function을 추가하는 이유.
* -다른 js파일에서 같은 이름(init, save)의 함수를 정의할 경우, 브라우저의 스코프는 공용공간이기 때문에 나중에 로딩된 함수가 기존 것을 덮어씌워버리기 때문.
* index.js만의 유효범위를 만들어 주기 위해 var main이란 객체를 만들어 해당 객체에서 모든 function을 선언하여 사용함다.
* p.142 참고.
* */
/*
* var a = {
    b:function(){ // b 는 key이지만 변수의 역할을 하며 이러한 key를 속성(property)라고 부른다.
    }
};
*
* */

var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        if (!data.author|| !data.title || !data.content) {
            alert('값을 모두 입력해주세요!');
        }

        if (confirm("글을 등록하시겠습니까?")) {
            $.ajax({
                type: 'POST',
                url: '/api/v1/posts',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('글이 등록되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();