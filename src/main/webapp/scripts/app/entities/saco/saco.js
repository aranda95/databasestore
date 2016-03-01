'use strict';

angular.module('databasestoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('saco', {
                parent: 'entity',
                url: '/sacos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.saco.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/saco/sacos.html',
                        controller: 'SacoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('saco');
                        $translatePartialLoader.addPart('modelo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('saco.detail', {
                parent: 'entity',
                url: '/saco/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.saco.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/saco/saco-detail.html',
                        controller: 'SacoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('saco');
                        $translatePartialLoader.addPart('modelo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Saco', function($stateParams, Saco) {
                        return Saco.get({id : $stateParams.id});
                    }]
                }
            })
            .state('saco.new', {
                parent: 'saco',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/saco/saco-dialog.html',
                        controller: 'SacoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    modelo: null,
                                    cantidad: null,
                                    comentario: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('saco', null, { reload: true });
                    }, function() {
                        $state.go('saco');
                    })
                }]
            })
            .state('saco.edit', {
                parent: 'saco',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/saco/saco-dialog.html',
                        controller: 'SacoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Saco', function(Saco) {
                                return Saco.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('saco', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('saco.delete', {
                parent: 'saco',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/saco/saco-delete-dialog.html',
                        controller: 'SacoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Saco', function(Saco) {
                                return Saco.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('saco', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
