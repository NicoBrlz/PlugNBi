/**
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { ExprDTO } from './exprDTO';

export interface LiteralExprDTO extends ExprDTO { 
    intValue?: number;
    doubleValue?: number;
    strValue?: string;
}