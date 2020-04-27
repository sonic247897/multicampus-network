package kr.multi.bigdataShop.product.comment;

import java.util.List;

public interface ProductCommentDAO {
	int insert(ProductCommentDTO comment);
	List<ProductCommentDTO> list(String prd_no);
	List<CommentResultDTO> result();
}