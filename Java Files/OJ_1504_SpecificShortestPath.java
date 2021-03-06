import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class OJ_1504_SpecificShortestPath
{
	static int g_iVertexCnt ;
	static int g_iEdgeCnt ;
	static long [] [] g_ldrgPath ;
	static ArrayList < cInfo > [] g_alrgEdge ;
	static int g_iFirstVertex ;
	static int g_iSecondVertex ;
	
	
	public static void main ( String[] args ) throws IOException
	{
		BufferedReader br = new BufferedReader ( new InputStreamReader ( System.in ) ) ;
		StringTokenizer st ;
		
		
		
		st = new StringTokenizer ( br.readLine () ) ;
		g_iVertexCnt = Integer.parseInt ( st.nextToken () ) ;
		g_iEdgeCnt = Integer.parseInt ( st.nextToken () ) ;
		g_ldrgPath = new long [ g_iVertexCnt ] [ 3 ] ;
		g_alrgEdge = new ArrayList [ g_iVertexCnt ] ;
		
		
		
		for ( int i = 0 ; i < g_iVertexCnt ; ++i )
		{
			Arrays.fill ( g_ldrgPath [ i ] , Long.MAX_VALUE ) ;
		}
		for ( int i = 0 ; i < g_iVertexCnt ; ++i )
		{
			g_alrgEdge [ i ] = new ArrayList <> ();
		}
		for ( int i = 0 ; i < g_iEdgeCnt ; ++i )
		{
			int iStart = 0 ;
			int iEnd = 0 ;
			int iLength = 0 ;
			
			
			st = new StringTokenizer ( br.readLine () ) ;
			iStart = Integer.parseInt ( st.nextToken () ) - 1 ;
			iEnd = Integer.parseInt ( st.nextToken () ) - 1 ;
			iLength = Integer.parseInt ( st.nextToken () ) ;
			
			g_alrgEdge [ iStart ].add ( new cInfo ( iEnd , iLength , -1 ) ) ;
			g_alrgEdge [ iEnd ].add ( new cInfo ( iStart , iLength , -1 ) ) ;
		}
		
		st = new StringTokenizer ( br.readLine () ) ;
		g_iFirstVertex = Integer.parseInt ( st.nextToken () ) - 1 ;
		g_iSecondVertex = Integer.parseInt ( st.nextToken () ) - 1 ;
		
		g_ldrgPath [ 0 ] [ 0 ] = 0 ;
		
		doDijkstra () ;
		
		System.out.println ( g_ldrgPath [ g_iVertexCnt - 1 ] [ 2 ] != Long.MAX_VALUE ? g_ldrgPath [ g_iVertexCnt - 1 ] [ 2 ] : -1 ) ;
	}
	
	public static void doDijkstra ()
	{
		PriorityQueue < cInfo > pq = new PriorityQueue <> () ;
		cInfo cVertex ;
		cInfo cGetEdge ;
		cInfo cTemp ;
		int iVertexPassed = 0 ;
		int iTempPassed = 0 ;
		
		
		
		if ( 0 == g_iFirstVertex )
		{
			g_ldrgPath [ 0 ] [ 1 ] = 0 ;
		}
		
		
		pq.add ( new cInfo ( 0 , 0 , g_iFirstVertex == 0 ? 0 : -1 ) ) ;
		
		
		while ( ! pq.isEmpty () )
		{
			do
			{
				cVertex = pq.poll () ;
				
				iVertexPassed = cVertex.m_iPassedSpecific ;
				
				if ( ( 0 == cVertex.m_iPassedSpecific ) || ( -1 == cVertex.m_iPassedSpecific ) )
				{
					++ iVertexPassed ;
				}
			}
			while ( cVertex.m_lLength != g_ldrgPath [ cVertex.m_iVertex ] [ iVertexPassed ] && ! pq.isEmpty () ) ;
			
			if ( null != cVertex )
			{
				for ( int i = 0 ; i < g_alrgEdge[ cVertex.m_iVertex ].size () ; ++i )
				{
					cGetEdge = g_alrgEdge [ cVertex.m_iVertex ].get ( i ) ;
					cTemp = new cInfo ( cGetEdge ) ;
					cTemp.m_lLength += g_ldrgPath [ cVertex.m_iVertex ] [ iVertexPassed ] ;
					cTemp.m_iPassedSpecific = cVertex.m_iPassedSpecific ;
					
					if ( ( cTemp.m_iVertex == g_iFirstVertex ) &&
							( ( -1 == cVertex.m_iPassedSpecific ) || ( 1 == cVertex.m_iPassedSpecific ) ) )					// -1 -> 0 || 1 -> 2
					{
						cTemp.m_iPassedSpecific = cVertex.m_iPassedSpecific + 1 ;
					}
					else if ( ( cTemp.m_iVertex == g_iSecondVertex ) &&
							( ( -1 == cVertex.m_iPassedSpecific ) || ( 0 == cVertex.m_iPassedSpecific ) ) )					// -1 -> 0 || 1 -> 2
					{
						cTemp.m_iPassedSpecific = cVertex.m_iPassedSpecific + 2 ;
					}
					
					iTempPassed = cTemp.m_iPassedSpecific ;
					
					if ( ( 0 == cTemp.m_iPassedSpecific ) || ( -1 == cTemp.m_iPassedSpecific ) )
					{
						++ iTempPassed ;
					}
					
					if ( cTemp.m_lLength < g_ldrgPath [ cTemp.m_iVertex ] [ iTempPassed ] )
					{
						g_ldrgPath [ cTemp.m_iVertex ] [ iTempPassed ] = cTemp.m_lLength ;
						pq.add ( cTemp ) ;
					}
				}
			}
		}
	}
	
	static class cInfo implements Comparable < cInfo >
	{
		int m_iVertex ;
		long m_lLength ;
		int m_iPassedSpecific ;									// -1 not, 0 First, 1 Second, 2 Both
		
		public cInfo ( int iVertex , long lLength , int iPassedSpecific )
		{
			m_iVertex = iVertex ;
			m_lLength = lLength ;
			m_iPassedSpecific = iPassedSpecific ;
		}
		
		public cInfo ( cInfo cTemp )
		{
			m_iVertex = cTemp.m_iVertex ;
			m_lLength = cTemp.m_lLength ;
			m_iPassedSpecific = cTemp.m_iPassedSpecific ;
		}
		
		@Override
		public int compareTo ( cInfo cCompare )
		{
			return ( int ) ( this.m_lLength - cCompare.m_lLength ) ;
//			if ( this.m_lLength < cCompare.m_lLength )
//			{
//				return this.m_lLength - cCompare.m_lLength ;
//			}
//
//			return this.m_iVertex - cCompare.m_iVertex ;
		}
	}
}