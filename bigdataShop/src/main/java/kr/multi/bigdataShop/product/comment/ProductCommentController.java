package kr.multi.bigdataShop.product.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductCommentController {
	@Autowired
	ProductCommentService service;
	
	//댓글 등록
	@RequestMapping(value = "/comment/write.do", method=RequestMethod.POST)
	public String commentinsert(ProductCommentDTO comment) {
		// commentDTO 자동으로 대입
		service.insert(comment);
		// 주소를 직접 써줘야 하므로 redirect: 사용해야 함
		return "redirect:/product/read.do?prd_no="+comment.getPrd_no();
	}
	
	@RequestMapping("/comment/result.do")
	public ModelAndView result() {
		ModelAndView mav = new ModelAndView();
		List<CommentResultDTO> result = service.result();
		mav.addObject("result", result);
		mav.setViewName("comment/result");
		return mav;
	}
}


