#include <iostream>
#pragma warning(disable:4996)

using std :: cout ;
using std :: cin ;

void getCombination ( int iNum ) ;

int main ()
{
	int iNum = 0 ;
	int iN = 0 ;
	int iK = 0 ;

	

	cin >> iNum ;

	getCombination ( iNum ) ;


	return 0 ;
}

void getCombination ( int iNum )
{
	int ** idpPascal = new int * [ 31 ] ;
	int iK = 0 ;
	int iN = 0 ;



	for ( int i = 0 ; i < 31 ; ++i )
	{
		idpPascal [ i ] = new int [ i + 3 ] { 0 , } ;
	}

	idpPascal [ 0 ] [ 1 ] = 1 ;

	for ( int i = 1 ; i < 31 ; ++i )
	{
		for ( int j = 1 ; j < i + 2 ; ++j )
		{
			idpPascal [ i ] [ j ] = idpPascal [ i - 1 ] [ j - 1 ] + idpPascal [ i - 1 ] [ j ] ;
		}
	}

	for ( int i = 0 ; i < iNum ; ++i )
	{
		scanf ( "%d %d" , & iK , & iN ) ;
		printf ( "%d\n" , idpPascal [ iN ] [ iK + 1 ] ) ;
	}

	for ( int i = 0 ; i < iN + 1 ; ++i )
	{
		delete [] idpPascal [ i ] ;
	}

	delete [] idpPascal ;
}