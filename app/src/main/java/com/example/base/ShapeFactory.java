package com.example.base;

import java.util.Random;

import android.util.Log;

import com.example.Listenner.ComeBackAdapter;

public class ShapeFactory {
	public Shape shape;
	
	public int shapes[][][][] =
		{ 
			{
				{
					{1,1,0,0},
					{1,0,0,0},
					{1,0,0,0},
					{0,0,0,0},
				},
				{
					{1,0,0,0},
					{1,1,1,0},
					{0,0,0,0},
					{0,0,0,0},
					},
				{
					{0,1,0,0},
					{0,1,0,0},
					{1,1,0,0},
					{0,0,0,0},
				},
				
				{
					{1,1,1,0},
					{0,0,1,0},
					{0,0,0,0},
					{0,0,0,0},
				},
		
				
		  },
		  
		  //反向
		  {
			  {
				  {1,1,0,0},
				  {0,1,0,0},
				  {0,1,0,0},
				  {0,0,0,0},
			  },
			  
			  {
				  {1,1,1,0},
				  {1,0,0,0},
				  {0,0,0,0},
				  {0,0,0,0},
			  },
			  
			  {
				  {1,0,0,0},
				  {1,0,0,0},
				  {1,1,0,0},
				  {0,0,0,0},
			  },
			  
			  {
				  {0,0,1,0},
				  {1,1,1,0},
				  {0,0,0,0},
				  {0,0,0,0},
			  },
			  
			  
		  },
		  
		  //第二种
			{
			  {
					{1,1,0,0},
					{0,1,1,0},
					{0,0,0,0},
					{0,0,0,0},
				    },
				{
					{0,1,0,0},
					{1,1,0,0},
					{1,0,0,0},
					{0,0,0,0},
					},
					{
						{1,0,0,0},
						{1,1,0,0},
						{0,1,0,0},
						{0,0,0,0},
					},
			},
		  
		  //第三种
		  /*
		   * {
				  {0,0,0,0},
				  {0,0,0,0},
				  {0,0,0,0},
				  {0,0,0,0},
			  }
		   * 
		   */
		  
		  {
			  {
				  {0,1,0,0},
				  {0,1,0,0},
				  {0,1,0,0},
				  {0,1,0,0},
			  },
			  
			  {
				  {1,1,1,1},
				  {0,0,0,0},
				  {0,0,0,0},
				  {0,0,0,0},
			  }
			  
			  
		  },
		  //第四种
		  
		  {
			  {
				  {1,1,1,0},
				  {0,1,0,0},
				  {0,0,0,0},
				  {0,0,0,0},
			  },
			  
			  {
				  {1,0,0,0},
				  {1,1,0,0},
				  {1,0,0,0},
				  {0,0,0,0},
			  },
			  
			  {
				  {0,1,0,0},
				  {1,1,1,0},
				  {0,0,0,0},
				  {0,0,0,0},
			  },
			  
			  {
				  {0,1,0,0},
				  {1,1,0,0},
				  {0,1,0,0},
				  {0,0,0,0},
			  },
			  
		  },
		  
		  
		  //第五种
		  
		  {
			  {
				  {1,1,0,0},
				  {1,1,0,0},
				  {0,0,0,0},
				  {0,0,0,0},
			  }
			  
			  
			  
		  }
		  
		};
	
	public Shape getShape(ComeBackAdapter listener){
		
		Log.d("TAG===>","getShape");
		shape=new Shape();
		shape.setAdapter(listener);
        int type = new Random().nextInt(shapes.length);
		
		shape.setBody(shapes[type]);
		shape.setStatus(0);
		return shape;
	}
	

}