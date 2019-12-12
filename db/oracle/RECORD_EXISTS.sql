CREATE OR REPLACE FUNCTION RECORD_EXISTS (P_TABLE          VARCHAR2
                                         ,P_COLUMN         VARCHAR2
                                         ,P_COLUMN_VALUE   VARCHAR2)
    RETURN SMALLINT
IS
    V_QUERY    VARCHAR2 (4000);
    V_RESULT   SMALLINT;
BEGIN
    V_QUERY :=
           'SELECT COUNT(1) FROM '
        || P_TABLE
        || ' WHERE '
        || P_COLUMN
        || ' = '
        || P_COLUMN_VALUE;

    EXECUTE IMMEDIATE V_QUERY
        INTO V_RESULT;

    RETURN V_RESULT;
END;