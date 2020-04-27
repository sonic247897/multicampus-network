package kr.multi.bigdataShop.product.comment;

public class CommentResultDTO {
	public String word;
	public int count;
	
	public CommentResultDTO() {
		
	}

	public CommentResultDTO(String word, int count) {
		super();
		this.word = word;
		this.count = count;
	}

	@Override
	public String toString() {
		return "CommentResultDTO [word=" + word + ", count=" + count + "]";
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
