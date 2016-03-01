'use strict';

angular.module('databasestoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teka', {
                parent: 'entity',
                url: '/tekas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.teka.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/teka/tekas.html',
                        controller: 'TekaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('teka');
                        $translatePartialLoader.addPart('tipo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teka.detail', {
                parent: 'entity',
                url: '/teka/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'databasestoreApp.teka.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/teka/teka-detail.html',
                        controller: 'TekaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('teka');
                        $translatePartialLoader.addPart('tipo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Teka', function($stateParams, Teka) {
                        return Teka.get({id : $stateParams.id});
                    }]
                }
            })
            .state('teka.new', {
                parent: 'teka',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/teka/teka-dialog.html',
                        controller: 'TekaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    tipo: null,
                                    modelo: null,
                                    cantidad: null,
                                    comentario: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teka', null, { reload: true });
                    }, function() {
                        $state.go('teka');
                    })
                }]
            })
            .state('teka.edit', {
                parent: 'teka',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/teka/teka-dialog.html',
                        controller: 'TekaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Teka', function(Teka) {
                                return Teka.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teka', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teka.delete', {
                parent: 'teka',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/teka/teka-delete-dialog.html',
                        controller: 'TekaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Teka', function(Teka) {
                                return Teka.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teka', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
