
/**
 * SI_MD001_Out_SynServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.gome.vdp.mdm.outbound;

    /**
     *  SI_MD001_Out_SynServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SI_MD001_Out_SynServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SI_MD001_Out_SynServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SI_MD001_Out_SynServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for sI_MD001_Out_Syn method
            * override this method for handling normal response from sI_MD001_Out_Syn operation
            */
           public void receiveResultsI_MD001_Out_Syn(
                    com.gome.vdp.mdm.outbound.SI_MD001_Out_SynServiceStub.MT_MD001_Resp result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sI_MD001_Out_Syn operation
           */
            public void receiveErrorsI_MD001_Out_Syn(java.lang.Exception e) {
            }
                


    }
    