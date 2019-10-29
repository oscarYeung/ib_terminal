// JavaScript Document

$(function(){
  function select_simulated(select_style,bomb_con_style){
    $(document).click(function(){
      $(bomb_con_style).hide();
      })
    $(select_style).live('click',function(e){
      var thisinput=$(this);
      var local=$(this).position();
      var bomb_con=$(bomb_con_style);
        $(this).parents("li").siblings().find(bomb_con_style).hide();
        $(this).parent().find(bomb_con_style).width($(this).width());//下拉框的宽度
        $(this).parent().find(bomb_con_style).show();
        e?e.stopPropagation():event.cancelBubble = true;
        bomb_con.find("dd").click(function(e){
        var bomb_text=$(this).text();
        $(this).addClass("selected").siblings().removeClass("selected");
        $(this).parents(bomb_con_style).hide();
        $(this).parents("li").find(select_style).val(bomb_text);
        e?e.stopPropagation():event.cancelBubble = true;
        
    }); 
    });
     return false;
  }
  select_simulated(".provin_select",".provin_con");
  
    
 /* $(".local").focus(function(){
    $(this).addClass("local3");
  });
  $(".local").blur(function(){
    $(this).removeClass("local3");
  });*/
  })