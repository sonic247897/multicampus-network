package kr.multi.bigdataShop.product.comment;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository("productcommentdao")
public class ProductCommentDAOImpl implements ProductCommentDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<ProductCommentDTO> list(String prd_no) {
		return 
			sqlSession.selectList("kr.multi.bigdataShop.productcomment.list", prd_no);
	}
	
	@Override
	public int insert(ProductCommentDTO comment) {
		return 
			sqlSession.insert("kr.multi.bigdataShop.productcomment.insert", comment);
	}

	@Override
	public List<CommentResultDTO> result() {
		return
			sqlSession.selectList("kr.multi.bigdataShop.productcomment.result");
	}
	
	
}


