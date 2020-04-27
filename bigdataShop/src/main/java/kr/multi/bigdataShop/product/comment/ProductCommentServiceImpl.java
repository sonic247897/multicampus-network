package kr.multi.bigdataShop.product.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service
public class ProductCommentServiceImpl implements ProductCommentService {
	@Autowired
	@Qualifier("productcommentdao")
	ProductCommentDAO dao;

	@Override
	public List<ProductCommentDTO> commentList(String prd_no) {
		return dao.list(prd_no);
	}

	@Override
	public int insert(ProductCommentDTO comment) {
		return dao.insert(comment);
	}

	@Override
	public List<CommentResultDTO> result() {
		return dao.result();
	}


}


